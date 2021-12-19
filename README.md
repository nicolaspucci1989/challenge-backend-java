# Challenge Backend - Java Spring Boot (API)

## Objetivo

### Desarrollar una API para explorar el mundo de Disney

#### Consultas

```sql
select personaje.id,
       nombre,
       edad,
       peso,
       personaje.imagen,
       titulo,
       pelicula_serie.id,
       pelicula_serie.imagen
from personaje
         inner join pelicula_serie_personajes
                    on personaje.id = pelicula_serie_personajes.personajes_id
         inner join pelicula_serie
                    on pelicula_serie_personajes.peliculas_series_id = pelicula_serie.id
where personaje.id = 7
```

```sql
select ps.id, titulo, ps.imagen, calificacion, pj.id, nombre, pj.imagen
from pelicula_serie ps
         inner join pelicula_serie_personajes
                    on ps.id = pelicula_serie_personajes.peliculas_series_id
         inner join personaje pj on pelicula_serie_personajes.personajes_id = pj.id
where ps.id = 1
```