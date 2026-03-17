import { Component, OnInit } from '@angular/core';
import { IonicModule } from '@ionic/angular';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { TaskService, FocusSession } from '../planner/task.service';
import { AuthService } from '../../services/auth.service'; // ✅ AGGIUNGI QUESTA

@Component({
  selector: 'app-profile',
  standalone: true,
  imports: [IonicModule, CommonModule],
  templateUrl: './profile.page.html',
  styleUrls: ['./profile.page.scss']
})
export class ProfilePage implements OnInit {
  activeTab: string = 'profile';

  sessions: FocusSession[] = [];
  isLoadingSessions = true;

  // ✅ DATI DELL'UTENTE LOGGATO (invece di hardcoded)
  user = {
    username: '',
    email: '',
    registrationDate: '',
    totalTasks: 0,
    completedTasks: 0,
    totalFocusSessions: 0,
    totalStudyMinutes: 0
  };

  // ✅ STATISTICHE DINAMICHE
  stats = {
    studyHours: 0,
    completionPercent: 0
  };

  constructor(
    private router: Router,
    private taskService: TaskService,
    private authService: AuthService // ✅ INIETTA AuthService
  ) {}

  navigate(page: string) {
    this.activeTab = page;
    this.router.navigate(['/' + page]);
  }

  ngOnInit() {
    this.loadUserProfile();
    this.loadSessions();
  }

  // ✅ CARICA DATI DELL'UTENTE
  loadUserProfile() {
    // Ottieni username e email dal token JWT
    const token = this.authService.getToken();
    if (token) {
      try {
        const payload = JSON.parse(atob(token.split('.')[1]));
        this.user.username = payload.sub || 'Utente';
        this.user.email = payload.email || '';
        this.user.registrationDate = payload.iat ? new Date(payload.iat * 1000).toLocaleDateString('it-IT') : '';
      } catch (e) {
        console.error('Errore decodifica token:', e);
      }
    }

    // Carica statistiche aggiuntive
    this.loadUserStats();
  }

  // ✅ CARICA STATISTICHE UTENTE
  loadUserStats() {
    // Totale sessioni focus e minuti di studio
    this.taskService.getFocusSessions().subscribe({
      next: sessions => {
        this.user.totalFocusSessions = sessions.length;
        this.user.totalStudyMinutes = sessions.reduce((sum, s) => sum + s.minutes, 0);
      },
      error: err => console.error('Errore caricamento sessioni:', err)
    });

    // Totale task completate (da implementare nel backend se non c'è)
    // this.taskService.getCompletedTasks().subscribe(...);
  }

  // ✅ CARICA SESSIONI FOCUS
  loadSessions() {
    this.taskService.getFocusSessions().subscribe({
      next: sessions => {
        this.sessions = sessions.sort((a, b) => new Date(b.day || '').getTime() - new Date(a.day || '').getTime());
        this.isLoadingSessions = false;
        
        // Calcola ore di studio settimanali
        const lastWeekSessions = sessions.filter(s => {
          const sessionDate = new Date(s.day || '');
          const oneWeekAgo = new Date();
          oneWeekAgo.setDate(oneWeekAgo.getDate() - 7);
          return sessionDate >= oneWeekAgo;
        });
        
        const weeklyMinutes = lastWeekSessions.reduce((sum, s) => sum + s.minutes, 0);
        this.stats.studyHours = Math.round(weeklyMinutes / 60);
        this.stats.completionPercent = Math.min(100, Math.round((weeklyMinutes / (7 * 60)) * 100)); // Target: 7h/settimana
      },
      error: err => {
        console.error(err);
        this.isLoadingSessions = false;
      }
    });
  }

  // ✅ LOGOUT
  onLogout() {
    this.authService.logout();
    this.router.navigate(['/login']);
  }
}