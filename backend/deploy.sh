aws ecr get-login-password --region us-west-2 | docker login --username AWS --password-stdin 287500336229.dkr.ecr.us-west-2.amazonaws.com
docker buildx build -t backend --platform linux/amd64 . --load
docker tag backend:latest 287500336229.dkr.ecr.us-west-2.amazonaws.com/backend:latest
docker push 287500336229.dkr.ecr.us-west-2.amazonaws.com/backend:latest