# ผู้พัฒนา

1. ยศวัจน์ บวรศรีธนนนท์ 6510450879
2. ปริยวิศว์ เตชะกฤตเมธีธำรง 6510450593
3. ภูรี ลิ้มวงศ์รุจิรัตน์ 6510450844
4. ...
5. ...

# การติดตั้ง

## Download Project

```
git clone https://github.com/Particulate-Matter-2-5/restaurant-transport.git
```

## Frontend

### Project Setup

```bash
cd frontend
npm install
```

### Compile and Hot-Reload for Development

```bash
npm run dev
```

### Compile and Minify for Production

```bash
npm run build
```

## Backend

ในส่วน Backend (restaurant) ติดตั้ง JDK Version 21

- Database Name: sa2
- Backend Port: 8088
- For admin, manually change role in database user_role = 'ADMIN'
- สร้าง `.env` ใน restaurant/src/main/resources/

```bash
STRIPE_TEST_KEY=sk_test_51Q6VTPBb2nJBY3WlwqNj5Uu2qphozXdNlD8AREPgcMYoFmDlMV7lNmgqBSjOxJKM7mWZ6uvVQR8DUbHFQcWomM2i009gCYwWNX
JWT_TEST_KEY=ffc0d02ab13cf1b58ac77b6165b17d6654837e8c845e197774754a0efd375560
```

### Run

```bash
mvn spring-boot:run
```
