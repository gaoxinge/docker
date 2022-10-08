FROM ubuntu

RUN apt update
RUN apt install -y wget 
RUN apt install -y build-essential
RUN apt install -y mysql-client
RUN DEBIAN_FRONTEND=noninteractive TZ=Asia/Shanghai apt install -y openssh-server
RUN mkdir /var/run/sshd
RUN echo 'root:root' | chpasswd
RUN sed -ri 's/^#?PermitRootLogin\s+.*/PermitRootLogin yes/' /etc/ssh/sshd_config
RUN sed -ri 's/UsePAM yes/#UsePAM yes/g' /etc/ssh/sshd_config
RUN apt install -y git
RUN apt install -y vim
RUN apt install -y openjdk-8-jdk

WORKDIR /tmp
RUN wget --no-check-certificate https://go.dev/dl/go1.19.2.linux-amd64.tar.gz
RUN rm -rf /usr/local/go && tar -C /usr/local -xzf go1.19.2.linux-amd64.tar.gz
ENV PATH $PATH:/usr/local/go/bin

WORKDIR /gaoxinge

CMD ["/usr/sbin/sshd", "-D"]
