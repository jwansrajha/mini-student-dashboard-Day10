# Mini Student Dashboard — Day 10 (Angular + Spring Boot + PostgreSQL)

مشروع تعليمي بسيط: عرض الطلاب (GET) وإضافة طالب (POST). البيانات محفوظة فعلًا في PostgreSQL.

## 0. الأدوات اللازمة

- Java JDK 21: جرّبي `java -version`
- PostgreSQL + pgAdmin أو psql
- Maven: جرّبي `mvn -version` (لو ما عندك Maven: أنشئي مشروع backend من [start.spring.io](https://start.spring.io) لتحصلي على ملفات `mvnw` الرسمية، وانسخي ملفات هذا المجلد إليه)
- Node.js متوافق مع Angular 21: Node 20.19+ أو 22.12+ أو 24+، و npm: جرّبي `node -v` و `npm -v`
- VS Code

## 1. اعملي قاعدة البيانات

افتحي pgAdmin واختاري Databases > Create > Database، وسمّيها **student_demo** (بالحروف نفسها).

لا يلزم إنشاء جدول يدويًا: Spring Boot ينفّذ `schema.sql` و `data.sql` أول ما يشتغل.

تأكدي إنه PostgreSQL شغال على port 5432. لو الـUsername مش `postgres` استخدمي `DB_USER`.

## 2. شغّلي Backend

افتحي Terminal داخل `student-backend`.

**macOS / Linux:**

```bash
export DB_USER=postgres
export DB_PASSWORD='YOUR_POSTGRES_PASSWORD'
mvn spring-boot:run
```

**Windows PowerShell:**

```powershell
$env:DB_USER="postgres"
$env:DB_PASSWORD="YOUR_POSTGRES_PASSWORD"
mvn spring-boot:run
```

استبدلي YOUR_POSTGRES_PASSWORD بكلمة مرور PostgreSQL الخاصة بجهازك. لا تكتبي كلمة المرور داخل الكود ولا ترفعيها على GitHub.

إذا أنتِ أصلًا بتستخدمي مستخدم مختلف (مثل jwanrajha) حطيه بدل postgres.

**اختبار:** افتحي `http://localhost:8080/api/students` لازم تشوفي JSON فيه Jwan وLina وMarwan.

إذا Backend رفض الاتصال بقاعدة البيانات: راجعي اسم database، username، password، port 5432.

> ملاحظة: إذا ما عندك أمر `mvn` لكن عندك ملف `mvnw` من Spring Initializr، استخدمي `./mvnw spring-boot:run` على Mac/Linux أو `mvnw.cmd spring-boot:run` على Windows.

## 3. شغّلي Frontend

**افتحي Terminal جديد** داخل `student-frontend` (اتركي Backend شغالًا في الأول).

```bash
npm install
npm start
```

افتحي `http://localhost:4200`، المفروض تشوفي الطلاب الثلاثة. املئي الاسم والعلامة واضغطي Add student، ثم اضغطي Refresh وتأكدي إنه الطالب ما اختفى. ممكن تشوفيه أيضًا في pgAdmin:

```sql
SELECT * FROM students ORDER BY id;
```

## 4. كيف بنعمل المشاريع من الصفر؟

### إنشاء Angular

بـTerminal خارج المشروع، بعد تثبيت Angular CLI:

```bash
npm install -g @angular/cli@21
ng new student-frontend --routing=false --style=css
```

الـCLI بيعمل Workspace جاهز. انسخي ملفات `src/app`, `src/main.ts`, `src/styles.css` من المشروع المرفق، وتأكدي من وجود `provideHttpClient()`، وشغلي `npm start`.

### إنشاء Spring Boot

ادخلي [start.spring.io](https://start.spring.io):

- Project: Maven
- Language: Java
- Spring Boot: 3.5.x (مستقر، إصدار المشروع المرفق 3.5.16)
- Group: `com.example`
- Artifact: `student-backend`
- Package: `com.example.student`
- Java: 21
- Dependencies: **Spring Web**, **Spring JDBC**, **PostgreSQL Driver**

اضغطي Generate، فكّي ZIP، وانسخي `StudentController.java`, `StudentBackendApplication.java` وملفات `application.properties`, `schema.sql`, `data.sql` من المشروع المرفق إلى نفس المسارات. وقتها شغليه باستخدام Maven Wrapper اللي بيولّده Spring Initializr.

## 5. أهم الملفات — وليش؟

- `student-frontend/src/app/app.html`: شكل صفحة الطلاب والفورم.
- `student-frontend/src/app/app.ts`: طلب GET وPOST باستخدام Angular HttpClient.
- `student-frontend/src/app/student.ts`: TypeScript interface لشكل بيانات الطالب.
- `student-frontend/src/main.ts`: تشغيل Angular + توفير `provideHttpClient()`.
- `student-backend/.../StudentController.java`: REST API endpoints.
- `student-backend/.../application.properties`: معلومات الاتصال بقاعدة البيانات.
- `student-backend/.../schema.sql`: إنشاء جدول students.
- `student-backend/.../data.sql`: إدخال 3 طلاب مبدئيًا.
- `student-backend/pom.xml`: مكتبات Spring Web وJDBC وPostgreSQL.

## 6. تتبعي حركة البيانات

**GET:** Angular (HttpClient GET) → Spring Boot (@GetMapping) → SQL SELECT → PostgreSQL → JSON → Angular تعرض الطلاب.

**POST:** Angular form (HttpClient POST مع JSON) → Spring Boot (@PostMapping) → SQL INSERT → PostgreSQL → Student JSON → Angular تعرض الطالب الجديد.

- `@RestController`: الClass بيرجع data عبر HTTP.
- `@GetMapping`: التعامل مع GET.
- `@PostMapping`: التعامل مع POST.
- `JdbcTemplate`: تنفيذ SQL من Java.
- `@CrossOrigin`: السماح لصفحة localhost:4200 بالاتصال على localhost:8080 من المتصفح.
- `subscribe`: معالجة نتيجة HTTP أو الخطأ لما يوصل.
- `signal`: متغير Angular تفاعلي لما يتغير بتتحدث الواجهة.
- `[(ngModel)]`: ربط مدخلات المستخدم مع متغيرات الـComponent.

## 7. أخطاء شائعة

- CORS error: افحصي وجود `@CrossOrigin(origins = "http://localhost:4200")` على Controller.
- 404 على `/api/students`: تأكدي إن Backend شغال والعنوان صحيح.
- Database authentication failed: افحصي DB_USER و DB_PASSWORD.
- `mvn: command not found`: ثبتي Maven أو استخدمي `mvnw` من المشروع المولد من Spring Initializr.
- Angular connection refused: شغلي Backend قبل Frontend.
- Node version unsupported: حدّثي Node.js لإصدار مناسب لـAngular 21.

## تنبيه

المشروع تعليمي وليس تطبيقًا إنتاجيًا: البيانات تجريبية، وواجهة الـAPI لا تحتوي على تسجيل دخول أو صلاحيات.
