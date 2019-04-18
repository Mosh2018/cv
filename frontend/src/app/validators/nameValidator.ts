import {AbstractControl} from '@angular/forms';

export function forbiddenNameValidator(control: AbstractControl) {
  let forbidden = false;
  let message = '';
  console.log(control.value);
  if (control.value && control.value.length < 2) {
    forbidden = true;
    message = 'First name can not shorter than 2 characters';
  }
  if (control.value && control.value.length > 20) {
    forbidden = true;
    message = 'First name can not longer than 2 characters';
  }
  if (forbidden) {
    console.log(message);
    return {text: message};
  }
  return null;
}
