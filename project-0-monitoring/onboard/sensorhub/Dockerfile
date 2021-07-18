# Python Base
From ubuntu:latest

# Install dependencies
RUN apt-get update -y \
    && apt-get upgrade -y \
    && apt-get clean all

RUN apt-get update -y && \
    apt-get install python3-pip -y

RUN pip3 install smbus

# Set the work directory
WORKDIR /usr/src/app

# Copy all the files from the current directory to the working directory
COPY . .

# Target Python script to run
CMD ["sensorhub-daq.py"]

# Set the entry point
ENTRYPOINT ["python3"]

# docker build -t smart-home-iot/sensorhub-daq .
# docker run --privileged -it smart-home-iot/sensorhub-daq
# docker run --device /dev/i2c-1 smart-home-iot/sensorhub-daq