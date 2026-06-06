Android + Desktop

Фичи: 
* вход/регистрация (локальная бд)
* Главный экран
* Избранное
* Профиль пользователя
* поиск ADOP(Astronomy Picture of the Day) по дням
* Информация о планетах

Состав команды:
* Макарова Арина 11-304
* Кадырова Алия 11-304


нужно запустить API через Docker:
```
docker build -t apod-api https://github.com/nasa/apod-api.git
docker run -d -p 5000:5000 --name apod-api-container apod-api
```

Ссылка на скринкаст: https://drive.google.com/file/d/11UclSFbuTYf4X33A86JwxwLgOzXSU3PG/view?usp=sharing
