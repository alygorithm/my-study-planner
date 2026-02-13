import { Component, Input } from '@angular/core';
import { IonicModule, ModalController } from '@ionic/angular';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { Task } from '../task.model';

@Component({
  selector: 'app-add-task-modal',
  standalone: true,
  imports: [IonicModule, CommonModule, FormsModule],
  template: `
    <ion-header>
      <ion-toolbar>
        <ion-title><b>Aggiungi Task</b></ion-title>
        <ion-buttons slot="end">
          <ion-button class="close-btn" (click)="close()">✕</ion-button>
        </ion-buttons>
      </ion-toolbar>
    </ion-header>

    <ion-content class="add-task-modal">
      <div class="form-container">

        <!-- Data selezionata -->
        <div class="selected-date">
          <span>Data: {{ formattedDate }}</span>
        </div>

        <!-- Titolo -->
        <ion-item>
          <ion-label position="stacked">Titolo *</ion-label>
          <ion-input 
            [(ngModel)]="taskTitle" 
            name="title"
            placeholder="Cosa devi fare?">
          </ion-input>
        </ion-item>

        <!-- Materia -->
        <ion-item>
          <ion-label position="stacked">Materia</ion-label>
          <ion-input 
            [(ngModel)]="taskSubject" 
            name="subject"
            placeholder="Es. Matematica...">
          </ion-input>
        </ion-item>

        <!-- Descrizione -->
        <ion-item>
          <ion-label position="stacked">Descrizione</ion-label>
          <ion-textarea 
            [(ngModel)]="taskDescription" 
            name="description"
            placeholder="Dettagli..."
            rows="3">
          </ion-textarea>
        </ion-item>

        <!-- Priorità con pulsanti -->
        <ion-item class="priority-item">
          <ion-label position="stacked">Priorità</ion-label>
          
          <div class="priority-buttons">
            <button 
              type="button"
              [class.active]="taskPriority === 'alta'"
              (click)="setPriority('alta')"
              class="priority-btn alta">
              <ion-icon name="arrow-up-circle-outline"></ion-icon>
              Alta
            </button>
            
            <button 
              type="button"
              [class.active]="taskPriority === 'media'"
              (click)="setPriority('media')"
              class="priority-btn media">
              <ion-icon name="remove-circle-outline"></ion-icon>
              Media
            </button>
            
            <button 
              type="button"
              [class.active]="taskPriority === 'bassa'"
              (click)="setPriority('bassa')"
              class="priority-btn bassa">
              <ion-icon name="arrow-down-circle-outline"></ion-icon>
              Bassa
            </button>
          </div>
        </ion-item>

        <!-- Pulsante Salva -->
        <ion-button expand="full" class="save-btn" (click)="saveTask()" [disabled]="!canSave">
          <ion-icon name="save-outline" slot="start"></ion-icon>
          Crea Task
        </ion-button>
      </div>
    </ion-content>
  `,
  styleUrls: ['./add-task.modal.scss']
})
export class AddTaskModal {

  @Input() day!: string;

  taskTitle = '';
  taskDescription = '';
  taskSubject = '';
  taskPriority = 'media';

  constructor(private modalCtrl: ModalController) {}

  // Formatta la data in italiano
  get formattedDate(): string {
    if (!this.day) return 'Nessuna data';
    const date = new Date(this.day);
    return date.toLocaleDateString('it-IT', {
      weekday: 'short',
      day: 'numeric',
      month: 'short',
      year: 'numeric'
    });
  }

  // Calcola durata in base alla priorità
  private estimateDuration(priority: string): number {
    switch (priority) {
      case 'alta': return 90;
      case 'media': return 60;
      case 'bassa': return 30;
      default: return 60;
    }
  }

  // Imposta priorità
  setPriority(priority: string) {
    this.taskPriority = priority;
  }

  // Verifica se si può salvare
  get canSave(): boolean {
    return this.taskTitle.trim().length > 0;
  }

  // Salva task
  saveTask() {
    if (!this.canSave) return;

    const duration = this.estimateDuration(this.taskPriority);

    const newTask: Task = {
      title: this.taskTitle.trim(),
      description: this.taskDescription.trim(),
      subject: this.taskSubject.trim(),
      priority: this.taskPriority,
      duration: duration,
      day: this.day
    };

    this.modalCtrl.dismiss(newTask);
  }

  close() {
    this.modalCtrl.dismiss();
  }
}