#!/bin/bash

# 在6378端口上启动一个Redis实例
redis-server --port 6378 &

# 在6380端口上启动一个Redis实例,并且作为6378的从库
redis-server --port 6380 --slaveof 127.0.0.1 6378 &

# 启动一个Sentinel监听主库,Sentinel会自动的去检测从库
redis-server sentinel.conf --sentinel
