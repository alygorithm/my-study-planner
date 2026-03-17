import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { IonicModule, ToastController } from '@ionic/angular';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { AuthService } from '../../services/auth.service';
import { addIcons } from 'ionicons';
import { eyeOutline, eyeOffOutline } from 'ionicons/icons';

@Component({
  selector: 'app-registrazione',
  templateUrl: './registrazione.page.html',
  styleUrls: ['./registrazione.page.scss'],
  standalone: true,
  imports: [
    IonicModule,
    CommonModule,
    FormsModule
  ]
})
export class RegistrazionePage {

  username = '';
  email = '';
  password = '';
  confirmPassword = '';

  submitted = false;
  showRules = false;
  showPassword = false;
  showConfirmPassword = false;
  isLoading = false;

  validDomains = [
    '@gmail.com', '@outlook.com', '@hotmail.com', '@live.com',
    '@yahoo.com', '@icloud.com', '@me.com', '@mac.com',
    '@proton.me', '@protonmail.com', '@aol.com', '@zoho.com',
    '@libero.it', '@virgilio.it', '@alice.it', '@tim.it',
    '@tiscali.it', '@fastwebnet.it', '@aruba.it'
  ];

  constructor(
    private toastCtrl: ToastController,
    private authService: AuthService,
    private router: Router
  ) {
    addIcons({
      'eye-outline': eyeOutline,
      'eye-off-outline': eyeOffOutline
    });
  }

  /* ================= VALIDAZIONI ================= */
  isValidUsername(username: string): boolean {
    return username ? /^[a-zA-Z0-9_]{3,}$/.test(username) : false;
  }

  isValidEmailDomain(email: string): boolean {
    return email ? this.validDomains.some(domain => email.endsWith(domain)) : false;
  }

  isValidPassword(password: string): boolean {
    if (!password) return false;
    return (
      password.length >= 8 &&
      /[A-Z]/.test(password) &&
      /[a-z]/.test(password) &&
      /[0-9]/.test(password) &&
      /[!@#$%^&*?_]/.test(password)
    );
  }

  get hasMinLength() { return this.password.length >= 8; }
  get hasUpper() { return /[A-Z]/.test(this.password); }
  get hasLower() { return /[a-z]/.test(this.password); }
  get hasNumber() { return /[0-9]/.test(this.password); }
  get hasSpecial() { return /[!@#$%^&*?_]/.test(this.password); }

  /* ================= NAVIGAZIONE ================= */
  vaiAlLogin() {
    this.router.navigate(['/login']);
  }

  /* ================= REGISTRAZIONE ================= */
  registrazione() {
    this.submitted = true;

    if (!this.username || !this.email || !this.password || !this.confirmPassword) {
      this.showToast('Compila tutti i campi');
      return;
    }

    if (!this.isValidUsername(this.username)) {
      this.showToast('Username non valido');
      return;
    }

    if (!this.isValidEmailDomain(this.email)) {
      this.showToast('Dominio email non consentito');
      return;
    }

    if (!this.isValidPassword(this.password)) {
      this.showToast('Password non valida');
      return;
    }

    if (this.password !== this.confirmPassword) {
      this.showToast('Le password non coincidono');
      return;
    }

    this.isLoading = true;

    this.authService.register({
      username: this.username,
      email: this.email,
      password: this.password
    }).subscribe({
      next: () => {
        this.showToastSuccess('Registrazione completata!');
        this.router.navigate(['/login']);
      },
      error: () => {
        this.showToast('Errore registrazione');
        this.isLoading = false;
      },
      complete: () => this.isLoading = false
    });
  }

  /* ================= TOAST ================= */
  async showToast(message: string) {
    const toast = await this.toastCtrl.create({
      message,
      duration: 2500,
      color: 'danger'
    });
    toast.present();
  }

  async showToastSuccess(message: string) {
    const toast = await this.toastCtrl.create({
      message,
      duration: 2500,
      color: 'success'
    });
    toast.present();
  }

  /* ================= PASSWORD BOX ================= */
  onPasswordFocus() {
    this.showRules = true;
  }

  onPasswordBlur() {
    // nasconde la box solo se non si clicca su di essa
    setTimeout(() => this.showRules = false, 150);
  }

}
