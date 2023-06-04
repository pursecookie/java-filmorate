
<h1 align="center">Filmorate</h1>


<p align="center"><i>Filmorate поможет вам с выбором фильма для вечернего просмотра в зависимости от оценок пользователей по всему миру</i></p>

<div style="text-align: center;">

![](https://github.com/pursecookie/java-filmorate/blob/add-friends-likes/src/main/resources/NarVm4RkNq0.jpg)

</div>


## Что умеет Filmorate?


- выводить топ лучших фильмов по версии пользователей;
- отображать фильмы по жанрам;
- разделять фильмы по рейтингу Ассоциации кинокомпаний;
- у вас будет собственный профиль, где будут храниться все понравившиеся вам фильмы;
- вы можете добавлять в друзья других пользователей.

*Остальные возможности приложения находятся в разработке.*

## Схема базы данных


![](https://github.com/pursecookie/java-filmorate/blob/add-friends-likes/src/main/resources/schema.png)



## Примеры запросов для основных операций приложения


<details>
  <summary><b>Фильмы</b></summary>

🆕Создать фильм
  ```SQL
  INSERT INTO films (name, description, release_date, duration, rating_id)
  VALUES (?,?,?,?,?)
  ```

ℹ️Вывести информацию о фильме по id
  ```SQL
  SELECT *
  FROM films
  WHERE film_id = ?
  ```

📄Вывести список всех фильмов
  ```SQL
  SELECT * 
  FROM films
  ```

🔄Обновить информацию о фильме
  ```SQL
  UPDATE films SET name = ?, description = ?, release_date = ?, duration = ?, rating_id = ?
  WHERE film_id = ?
  ```

❌Удалить фильм по id
  ```SQL
  DELETE FROM films
  WHERE film_id = ?
  ```

  </details>  

<details>
  <summary><b>Лайки</b></summary>

💓Поставить фильму лайк
  ```SQL
  INSERT INTO likes (film_id, user_id)
  VALUES (?,?)
  ```

🔝Вывести ТОП-10 популярных фильмов
  ```SQL
  SELECT *
  FROM (
  SELECT film_id, COUNT (user_id) AS like_count
  FROM likes
  GROUP BY film_id
  ORDER BY like_count DESC
  ) AS l
  LEFT OUTER JOIN films AS f ON l.film_id = f.film_id
  LIMIT 10
  ```

💔Удалить лайк у фильма
  ```SQL
  DELETE FROM likes
  WHERE film_id = ?
  AND user_id = ?
  ```

  </details>

<details>
  <summary><b>Пользователи</b></summary>

🆕Создать пользователя
  ```SQL
  INSERT INTO users (login, name, email, birthday)
  VALUES (?,?,?,?)
  ```

ℹ️Вывести информацию о пользователе по id
  ```SQL
  SELECT * FROM users
  WHERE user_id = ?
  ```

📄<i>Вывести список всех пользователей</i>
  ```SQL
  SELECT * 
  FROM users
  ```

🔄Обновить информацию о пользователе
  ```SQL
  UPDATE users SET login = ?, name = ?, email = ?, birthday = ?
  WHERE user_id = ?
  ```

❌Удалить пользователя по id
  ```SQL
  DELETE FROM users
  WHERE user_id = ?
  ```

  </details>

<details>
  <summary><b>Дружба</b></summary>

✅Добавить пользователя в друзья
  ```SQL
  INSERT INTO friendships (user_from, user_to, status)
  VALUES (?,?,?)
  ```

👫Вывести список всех друзей пользователя
  ```SQL
  SELECT f.user_to AS user_id, u.login, u.name, u.email, u.birthday
  FROM friendships AS f
  LEFT OUTER JOIN users AS u ON f.user_to = u.user_id
  WHERE f.user_from = ?
  ORDER BY user_id
  ```

❌Удалить пользователя из друзей
  ```SQL
  DELETE FROM friendships
  WHERE user_from = ?
  AND user_to = ?
  ```

  </details>
