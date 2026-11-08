import { Component, OnInit, signal } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { Student } from './student';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App implements OnInit {
  private readonly apiUrl = 'http://localhost:8080/api/students';

  students = signal<Student[]>([]);
  loading = signal(false);
  message = signal('');
  newName = '';
  newGrade: number | null = null;

  constructor(private http: HttpClient) {}

  // When the component starts, ask Spring Boot for the students.
  ngOnInit(): void {
    this.loadStudents();
  }

  loadStudents(): void {
    this.loading.set(true);
    this.message.set('');

    this.http.get<Student[]>(this.apiUrl).subscribe({
      next: data => {
        this.students.set(data);
        this.loading.set(false);
      },
      error: () => {
        this.message.set('Could not load students. Is Spring Boot running?');
        this.loading.set(false);
      }
    });
  }

  addStudent(): void {
    const name = this.newName.trim();
    const grade = this.newGrade;

    if (!name || grade === null || !Number.isInteger(grade) || grade < 0 || grade > 100) {
      this.message.set('Enter a name and a grade between 0 and 100.');
      return;
    }

    // POST sends JSON to Spring Boot. The backend inserts a row in PostgreSQL.
    this.http.post<Student>(this.apiUrl, { name, grade }).subscribe({
      next: createdStudent => {
        this.students.update(current => [...current, createdStudent]);
        this.newName = '';
        this.newGrade = null;
        this.message.set('Student added successfully!');
      },
      error: () => this.message.set('Could not add student. Check the backend.')
    });
  }
}
