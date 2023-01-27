# sns_publish

Scripts to submit a TRE input message to the TRE input SNS topic for a given
consignment stored in s3.

To submit a message to TRE:

* Identify (or create) a consignment and consignment checksum file in AWS s3
  * For example, [`TDR-2022-D6WD.tar.gz` and `TDR-2022-D6WD.tar.gz.sha256`](../../../test/resources/small-normal-batch/bagit/v1-2)
* Set the following environment variable values accordingly:

  ```bash
  CONSIGNMENT_REF='?'
  CONSIGNMENT_TYPE='?'
  AWS_PROFILE_MANAGEMENT='?'
  AWS_PROFILE_NON_PROD='?'
  S3_BUCKET_NAME='?'
  S3_PATH_PREFIX='?'
  TRE_ENVIRONMENT_NAME='?'
  TRE_IN_TOPIC_NAME="${TRE_ENVIRONMENT_NAME}-?"
  ```
  
* Set the TRE input topic's ARN:

  ```bash
  AWS_QUERY='Topics[?ends_with(TopicArn, `:'"${TRE_IN_TOPIC_NAME:?}"'`) == `true`].TopicArn | [0]'
  
  TRE_IN_TOPIC_ARN="$(
  aws --profile "${AWS_PROFILE_NON_PROD:?}" \
    sns list-topics \
      --query "${AWS_QUERY:?}" \
      --output text
  )"
  
  echo "${TRE_IN_TOPIC_ARN:?}"
  ```

* Generate and send the event to TRE:

  ```bash
  ./sns_publish.sh \
    "${TRE_ENVIRONMENT_NAME:?}" \
    "${TRE_IN_TOPIC_ARN:?}" \
    "${S3_BUCKET_NAME:?}" \
    "${S3_PATH_PREFIX}${CONSIGNMENT_REF:?}.tar.gz" \
    "${S3_PATH_PREFIX}${CONSIGNMENT_REF:?}.tar.gz.sha256" \
    "${CONSIGNMENT_REF:?}" \
    "${CONSIGNMENT_TYPE:?}" \
    "${AWS_PROFILE_MANAGEMENT:?}" \
    "${AWS_PROFILE_NON_PROD}"
  ```
  