# 准备3台2核2g机器

现在一台虚拟机中初始化所有通用内容

1. 更新源 并安装所需工具

```bash
yum -y update
yum install -y conntrack ipvsadm ipset jq sysstat curl iptables libseccomp
```

2. 安装 docker

```bash
# 卸载之前的docker
sudo yum remove docker docker latest docker-latest-logrotate docker-logrotate docker-engine docker-client docker-client-latest docker-common

# 安装所需工具
sudo yum install -y yum-utils device-mapper-persistent-data lvm2

# 设置docker仓库
sudo yum-config-manager \
    --add-repo http://mirrors.aliyun.com/docker-ce/linux/centos/docker-ce.repo
    yum list | grep docker-ce

# 更新源缓存
sudo yum makecache fast

# 安装指定版本 docker
yum install -y docker-ce-18.09.0 docker-ce-cli-18.09.0 containerd.io

# 启动并设置开机启动 docker
sudo systemctl start docker && sudo systemctl enable docker
```

# 修改系统配置

```bash
# 关闭防火墙
systemctl stop firewalld && systemctl disable firewalld

# 关闭selinux
setenforce 0
sed -i 's/^SELINUX=enforcing$/SELINUX=permissive/' /etc/selinux/config

# 关闭swap
swapoff -a
sed -i '/swap/s/^\(.*\)$/#\1/g' /etc/fstab

# 配置iptables的ACCEPT规则
iptables -F && iptables -X && iptables -F -t nat && iptables -X -t nat && iptables -P FORWARD ACCEPT

# 设置系统参数
cat <<EOF >  /etc/sysctl.d/k8s.conf
net.bridge.bridge-nf-call-ip6tables = 1
net.bridge.bridge-nf-call-iptables = 1
EOF

sysctl --system

```



# 安装 kubeadm, kubelet and kubectl

1. 配置 yum 源

```bash
cat <<EOF > /etc/yum.repos.d/kubernetes.repo
[kubernetes]
name=Kubernetes
baseurl=http://mirrors.aliyun.com/kubernetes/yum/repos/kubernetes-el7-x86_64
enabled=1
gpgcheck=0
repo_gpgcheck=0
gpgkey=http://mirrors.aliyun.com/kubernetes/yum/doc/yum-key.gpg
       http://mirrors.aliyun.com/kubernetes/yum/doc/rpm-package-key.gpg
EOF
```

2. 安装 kubeadm, kubelet and kubectl

```bash
yum install -y kubeadm-1.14.0-0 kubelet-1.14.0-0 kubectl-1.14.0-0
```

3. docker和k8s设置同一个cgroup

```bash
# 1. 文件没内容的话，就新建；有的话，就加上这一句，注意文件的格式[逗号]
vi /etc/docker/daemon.json

# 2. 插入内容
{
	"exec-opts": ["native.cgroupdriver=systemd"]
}

# 3. 重启 docker
systemctl restart docker

# 4. kubelet，这边如果发现输出directory not exist，也说明是没问题的，大家继续往下进行即可
sed -i "s/cgroup-driver=systemd/cgroup-driver=cgroupfs/g" /etc/systemd/system/kubelet.service.d/10-kubeadm.conf

# 5 启动 kubelet
systemctl enable kubelet && systemctl start kubelet
```



# 设置并拉取 proxy/pause/scheduler等国内镜像

1. 创建 kubeadm.sh 脚本，用于拉取镜像/打tag/删除原有镜像

```bash
#!/bin/bash

set -e

KUBE_VERSION=v1.14.0
KUBE_PAUSE_VERSION=3.1
ETCD_VERSION=3.3.10
CORE_DNS_VERSION=1.3.1

GCR_URL=k8s.gcr.io
ALIYUN_URL=registry.cn-hangzhou.aliyuncs.com/google_containers

images=(kube-proxy:${KUBE_VERSION}
kube-scheduler:${KUBE_VERSION}
kube-controller-manager:${KUBE_VERSION}
kube-apiserver:${KUBE_VERSION}
pause:${KUBE_PAUSE_VERSION}
etcd:${ETCD_VERSION}
coredns:${CORE_DNS_VERSION})

for imageName in ${images[@]} ; do
  docker pull $ALIYUN_URL/$imageName
  docker tag  $ALIYUN_URL/$imageName $GCR_URL/$imageName
  docker rmi $ALIYUN_URL/$imageName
done
```

2. 执行脚本，并查看镜像

```bash
sh ./kubeadm.sh

docker images
```



# 使用虚拟机克隆，创建 worker1、worker2 虚拟机





# 每台机器上配置机器的 hosts 使三台机器可以互 ping

```bash
# 设置 hostname，并且修改hosts文件 [master、work1、work2]
sudo hostnamectl set-hostname master

# 在两个work机器上
sudo hostnamectl set-hostname work1
sudo hostnamectl set-hostname work2

# 编辑 hosts
vi /etc/hosts

# 添加如下内容
10.211.55.11 master
10.211.55.12 work1
10.211.55.13 work2

```



# kube init初始化master

## 初始化 master 节点

```bash
# 若要重新初始化集群状态 请执行下面这条命令
kubeadm reset

# 初始化命令
kubeadm init --kubernetes-version=1.14.0 \
    --apiserver-advertise-address=10.211.55.11 \
    --pod-network-cidr=10.244.0.0/16
```

## 根据 bash 界面提示 保存 kubeadm join 信息

```bash
Your Kubernetes control-plane has initialized successfully!

To start using your cluster, you need to run the following as a regular user:

  mkdir -p $HOME/.kube
  sudo cp -i /etc/kubernetes/admin.conf $HOME/.kube/config
  sudo chown $(id -u):$(id -g) $HOME/.kube/config

You should now deploy a pod network to the cluster.
Run "kubectl apply -f [podnetwork].yaml" with one of the options listed at:
  https://kubernetes.io/docs/concepts/cluster-administration/addons/

Then you can join any number of worker nodes by running the following on each as root:

# kubeadm join
kubeadm join 10.211.55.11:6443 --token sx96ht.hbqcwi46qeowmveh \
    --discovery-token-ca-cert-hash sha256:370465a8e92e3f0c003a85c813a1af6d645c79c2727b784cd84cd2e681ef3f3d
```

