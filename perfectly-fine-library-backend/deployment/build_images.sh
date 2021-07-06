#! /bin/bash
(cd $(dirname $0)/..;
  for i in $(ls | grep service);
  do
    (cd $i;
      docker build -t $i:latest .;
      docker tag $i:latest swr.cn-north-4.myhuaweicloud.com/fdse21group25/$i:latest
      docker push swr.cn-north-4.myhuaweicloud.com/fdse21group25/$i:latest
    )
  done;
)

