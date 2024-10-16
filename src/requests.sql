SELECT USERNAME
FROM USERS u

Продукт Invest 500

1.Пользователь использует как минимум один продукт с типом DEBIT.

SELECT DISTINCT USERNAME
FROM USERS u
JOIN TRANSACTIONS t ON t.USER_ID = u.ID
JOIN PRODUCTS p ON p.ID = t.PRODUCT_ID
WHERE p.TYPE='DEBIT'

2.Пользователь не использует продукты с типом INVEST.

SELECT DISTINCT USERNAME
FROM USERS u
JOIN TRANSACTIONS t ON t.USER_ID = u.ID
JOIN PRODUCTS p ON p.ID = t.PRODUCT_ID
WHERE p.TYPE!='INVEST'


3.Сумма пополнений продуктов с типом SAVING больше 1000 ₽.

SELECT DISTINCT USERNAME
FROM USERS u
JOIN TRANSACTIONS t ON t.USER_ID = u.ID
JOIN PRODUCTS p ON p.ID = t.PRODUCT_ID
WHERE p.TYPE!='SAVING' AND t.AMOUNT >1000


Продукт Top Saving

Пользователь использует как минимум один продукт с типом DEBIT.

SELECT DISTINCT USERNAME
FROM USERS u
JOIN TRANSACTIONS t ON t.USER_ID = u.ID
JOIN PRODUCTS p ON p.ID = t.PRODUCT_ID
WHERE p.TYPE='DEBIT'

Сумма пополнений по всем продуктам типа DEBIT больше или равна 50 000 ₽ ИЛИ Сумма пополнений по всем продуктам типа SAVING больше или равна 50 000 ₽.

SELECT DISTINCT USERNAME
FROM USERS u
JOIN TRANSACTIONS t ON t.USER_ID = u.ID
JOIN PRODUCTS p ON p.ID = t.PRODUCT_ID
WHERE (p.TYPE!='DEBIT' AND t.AMOUNT >=50_000) OR (p.TYPE!='SAVING' AND t.AMOUNT >=50_000)

Сумма пополнений по всем продуктам типа DEBIT больше, чем сумма трат по всем продуктам типа DEBIT.


