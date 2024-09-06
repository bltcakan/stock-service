# stock-service
### Uygulamayı Ayağa kaldırma 
#### 1.Siteminiz de docker uygulaması kurulu olmalı
#### 2.Projenin dizinine gidip

* docker build -t springboot-app .
* docker run -p 8080:8080 springboot-app

Komutları çalıştırılmalıdır 

### Ornek Requestler 
#### Sisteme test için 3 kullanıcı tanımlandı
* admin
* user1
* user2
#### Şifreleri admin123

### Match Order Sadece Admin Kullanıcısı Kullanabilir
####  curl -u admin:admin123 -X PUT -v http://localhost:8080/api/v1/admin/1/match -> verilen order ı match eder






