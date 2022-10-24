select first_name,
       birthday
from users
WHERE birthday > '1999-10-23'
ORDER BY first_name ASC LIMIT
  3
OFFSET 0
