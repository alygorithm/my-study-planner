import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { IonicModule, ToastController, LoadingController } from '@ionic/angular';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { AuthService } from '../../services/auth.service';
import { addIcons } from 'ionicons';
import { eyeOutline, eyeOffOutline } from 'ionicons/icons';

@Component({
  selector: 'app-login',
  templateUrl: './login.page.html',
  styleUrls: ['./login.page.scss'],
  standalone: true,
  imports: [
    IonicModule,
    FormsModule,
    CommonModule
  ]
})
export class LoginPage {
  email = '';
  password = '';
  submitted = false;
  isLoading = false;

  constructor(
    private router: Router,
    private toastCtrl: ToastController,
    private loadingCtrl: LoadingController,
    private authService: AuthService
  ) {
    addIcons({
      'eye-outline': eyeOutline,
      'eye-off-outline': eyeOffOutline
    });
  }

  async login() {
    this.submitted = true;

    if (!this.email || !this.password) {
      this.showToast('Inserisci email e password');
      return;
    }

    this.isLoading = true;
    const loading = await this.loadingCtrl.create({
      message: 'Accesso in corso...',
      spinner: 'crescent'
    });
    await loading.present();

    this.authService.login({ email: this.email, password: this.password }).subscribe({
      next: () => {
        loading.dismiss();
        this.showToastSuccess('Accesso effettuato!');
        setTimeout(() => this.router.navigate(['/planner']), 1000);
      },
      error: (err) => {
        loading.dismiss();
        this.showToast(err.message || 'Credenziali non valide');
      }
    });
  }

  goToRegistrazione() {
    this.router.navigate(['/registrazione']);
  }

  async showToast(message: string) {
    const toast = await this.toastCtrl.create({ message, duration: 3000, color: 'danger' });
    toast.present();
  }

  async showToastSuccess(message: string) {
    const toast = await this.toastCtrl.create({ message, duration: 2000, color: 'success' });
    toast.present();
  }
}
