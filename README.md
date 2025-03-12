# 🚀 Restaurant Transport

A restaurant management system for food ordering and delivery.

---

## 👑 Project Manager

- **ภูเบศ สิริเมธาวุฒิ** (6510450810)

## 📊 System Analyst

- **อภิญญา ลิ้มฮวบ** (6510451077)

## 💼 Business Analyst

- **พศิน เจียระศิริสิน** (6510450712)

## 👨‍💻 Developers

- **ยศวัจน์ บวรศรีธนนนท์** (6510450879)
- **ปริยวิศว์ เตชะกฤตเมธีธำรง** (6510450593)
- **ภูรี ลิ้มวงศ์รุจิรัตน์** (6510450844)
- **ภัควัฒน์ ปานกลาง** (6510450763)
- **ปริยานุช มั่งคั่ง** (6510450607)

---

## 📥 Installation

### 1️⃣ Clone the Repository

```sh
git clone https://github.com/Particulate-Matter-2-5/restaurant-transport.git
```

---

## 🎨 Frontend

### 🔧 Project Setup

```sh
cd frontend
npm install
```

### 🚀 Run in Development Mode

```sh
npm run dev
```

### 📦 Build for Production

```sh
npm run build
```

---

## 🛠️ Backend

### 📌 Prerequisites

- Install **JDK 21**
- Database Name: `sa2`
- Backend Port: `8088`
- To assign admin role, manually set `user_role = 'ADMIN'` in the database.

### 📝 Environment Variables

Create a `.env` file in `restaurant/src/main/resources/` with the following content:

```sh
STRIPE_TEST_KEY=sk_test_51Q6VTPBb2nJBY3WlwqNj5Uu2qphozXdNlD8AREPgcMYoFmDlMV7lNmgqBSjOxJKM7mWZ6uvVQR8DUbHFQcWomM2i009gCYwWNX
JWT_TEST_KEY=ffc0d02ab13cf1b58ac77b6165b17d6654837e8c845e197774754a0efd375560
```

### 🚀 Run Backend

```sh
mvn spring-boot:run
```

---

✅ **Now your system is ready to go!** 🚀
