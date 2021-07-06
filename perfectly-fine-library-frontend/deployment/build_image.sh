(
	cd $(dirname $0)/..;
	docker build -t fdse21group25/frontend .
	docker tag fdse21group25/frontend swr.cn-north-4.myhuaweicloud.com/fdse21group25/frontend
	docker push swr.cn-north-4.myhuaweicloud.com/fdse21group25/frontend
)