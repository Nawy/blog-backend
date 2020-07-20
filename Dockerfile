FROM oracle/graalvm-ce:20.1.0-java11 as graalvm
RUN gu install native-image

COPY . /home/app/blog-test
WORKDIR /home/app/blog-test

RUN native-image --no-server -cp build/libs/blog-test-*-all.jar

FROM frolvlad/alpine-glibc
RUN apk update && apk add libstdc++
EXPOSE 8080
COPY --from=graalvm /home/app/blog-test/blog-test /app/blog-test
ENTRYPOINT ["/app/blog-test"]
