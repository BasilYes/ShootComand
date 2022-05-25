FROM ubuntu:20.04
RUN apt update \
	&& apt install -y adb 
ADD ./app/build/intermediates/apk/debug /mnt/apk
