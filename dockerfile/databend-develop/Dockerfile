FROM ubuntu

RUN apt update
RUN apt install -y curl
RUN apt install -y build-essential
RUN apt install -y pkg-config
RUN apt install -y libssl-dev  
RUN apt install -y openssh-server
RUN mkdir /var/run/sshd
RUN echo 'root:root' | chpasswd
RUN sed -ri 's/^#?PermitRootLogin\s+.*/PermitRootLogin yes/' /etc/ssh/sshd_config
RUN sed -ri 's/UsePAM yes/#UsePAM yes/g' /etc/ssh/sshd_config

WORKDIR /tmp
RUN curl https://sh.rustup.rs -sSf | sh -s -- -y
ENV PATH $HOME/.cargo/bin:$PATH

WORKDIR /gaoxinge
RUN mkdir databend

CMD ["/usr/sbin/sshd", "-D"]
