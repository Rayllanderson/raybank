import boto3
from PIL import Image
from io import BytesIO

s3_client = boto3.client('s3')

def handler(event, context):
    # Extrair informações do evento S3
    bucket = event['Records'][0]['s3']['bucket']['name']
    key = event['Records'][0]['s3']['object']['key']
    
    # Baixar a imagem do S3
    response = s3_client.get_object(Bucket=bucket, Key=key)
    image_content = response['Body'].read()
    
    # Abrir a imagem com Pillow
    image = Image.open(BytesIO(image_content))
    
    # Criar a thumbnail
    thumbnail_size = (128, 128)
    image.thumbnail(thumbnail_size)
    
    # Salvar a thumbnail em um objeto BytesIO
    thumbnail_buffer = BytesIO()
    image.save(thumbnail_buffer, 'JPEG')
    thumbnail_buffer.seek(0)
    
    # Definir o novo nome do arquivo para a thumbnail
    thumbnail_key = f"thumbnails/{key}"
    
    # Upload da thumbnail para o S3
    s3_client.put_object(
        Bucket=bucket,
        Key=thumbnail_key,
        Body=thumbnail_buffer,
        ContentType='image/jpeg'
    )
    
    return {
        'statusCode': 200,
        'body': f'Thumbnail created and uploaded to {thumbnail_key}'
    }
