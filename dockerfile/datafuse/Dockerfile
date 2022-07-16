FROM ubuntu

RUN apt update
RUN apt install -y curl
RUN apt install -y build-essential
RUN apt install -y mysql-client
RUN DEBIAN_FRONTEND=noninteractive TZ=Asia/Shanghai apt install -y pkg-config
RUN apt install -y libssl-dev
RUN apt install -y cmake
RUN apt install -y clang
RUN DEBIAN_FRONTEND=noninteractive TZ=Asia/Shanghai apt install -y openssh-server
RUN mkdir /var/run/sshd
RUN echo 'root:root' | chpasswd
RUN sed -ri 's/^#?PermitRootLogin\s+.*/PermitRootLogin yes/' /etc/ssh/sshd_config
RUN sed -ri 's/UsePAM yes/#UsePAM yes/g' /etc/ssh/sshd_config
RUN apt install -y git
RUN apt install -y vim

WORKDIR /tmp
RUN curl https://sh.rustup.rs -sSf | sh -s -- -y
ENV PATH $HOME/.cargo/bin:$PATH

WORKDIR /gaoxinge

CMD ["/usr/sbin/sshd", "-D"]
