import json
import boto3
from PIL import Image
from io import BytesIO

s3 = boto3.client('s3')

def lambda_handler(event, context):
    for record in event['Records']:
        bucket = record['s3']['bucket']['name']
        key = record['s3']['object']['key']
        
        response = s3.get_object(Bucket=bucket, Key=key)
        image_data = response['Body'].read()
        
        image = Image.open(BytesIO(image_data))
        
        thumbnail_size = (128, 128)
        image.thumbnail(thumbnail_size)
        
        buffer = BytesIO()
        image.save(buffer, 'JPEG')
        buffer.seek(0)
        
        thumbnail_key = f"thumbnails/{key}"
        s3.put_object(Bucket=bucket, Key=thumbnail_key, Body=buffer, ContentType='image/jpeg')
        
    return {
        'statusCode': 200
    }
