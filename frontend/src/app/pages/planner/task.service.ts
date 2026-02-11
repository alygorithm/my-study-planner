import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { AuthService } from '../../services/auth.service';
import { Task } from './task.model';

export interface FocusSession {
  subject: string;
  minutes: number;
  completed: boolean;
  day?: string;
  taskId?: string;
}

@Injectable({
  providedIn: 'root'
})
export class TaskService {
  private apiUrl = `${environment.apiUrl}/api`;

  constructor(
    private http: HttpClient,
    private authService: AuthService
  ) {}

  private getAuthHeaders(): HttpHeaders {
    const token = this.authService.getToken();
    return new HttpHeaders({
      'Authorization': token ? `Bearer ${token}` : '',
      'Content-Type': 'application/json'
    });
  }

  getTasks(): Observable<Task[]> {
    const headers = this.getAuthHeaders();
    return this.http.get<Task[]>(`${this.apiUrl}/tasks`, { headers });
  }

  addTask(task: Task): Observable<Task> {
    const headers = this.getAuthHeaders();
    return this.http.post<Task>(`${this.apiUrl}/tasks`, task, { headers });
  }

  deleteTask(id: string): Observable<any> {
    const headers = this.getAuthHeaders();
    return this.http.delete(`${this.apiUrl}/tasks/${id}`, { headers });
  }

  updateTask(task: Task): Observable<Task> {
    const headers = this.getAuthHeaders();
    return this.http.put<Task>(`${this.apiUrl}/tasks/${task._id}`, task, { headers });
  }

  addFocusSession(session: FocusSession): Observable<FocusSession> {
    const headers = this.getAuthHeaders();
    return this.http.post<FocusSession>(`${this.apiUrl}/focus-sessions`, session, { headers });
  }

  getFocusSessions(): Observable<FocusSession[]> {
    const headers = this.getAuthHeaders();
    return this.http.get<FocusSession[]>(`${this.apiUrl}/focus-sessions`, { headers });
  }
}