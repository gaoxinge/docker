FROM ubuntu

RUN apt update
RUN apt install -y wget 
RUN apt install -y mysql-client
RUN apt install -y openssh-server
RUN mkdir /var/run/sshd
RUN echo 'root:root' | chpasswd
RUN sed -ri 's/^#?PermitRootLogin\s+.*/PermitRootLogin yes/' /etc/ssh/sshd_config
RUN sed -ri 's/UsePAM yes/#UsePAM yes/g' /etc/ssh/sshd_config

WORKDIR /tmp
RUN wget --no-check-certificate https://go.dev/dl/go1.18.3.linux-amd64.tar.gz
RUN rm -rf /usr/local/go && tar -C /usr/local -xzf go1.18.3.linux-amd64.tar.gz
ENV PATH $PATH:/usr/local/go/bin

WORKDIR /gaoxinge
RUN mkdir tinysql

CMD ["/usr/sbin/sshd", "-D"]
