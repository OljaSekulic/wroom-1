import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { AuthService } from './modules/auth/service/auth.service';
import { LoggedUser } from './modules/auth/model/logged-user.model';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  title = 'Monolith';

  user: LoggedUser;

  constructor(private http: HttpClient,
    private authService: AuthService) { }

  ngOnInit(): void {
    // console.log('Greetings! Attempting to establish http communication with monolith back-end...');
    // this.http.get('http://localhost:8080/api/stub/test').subscribe(response => {
    //   console.log('Received a response: ', response);

    //   console.log('Testing POST');
    //   this.http.post('http://localhost:8080/api/stub/test', { test: "aaa"}).subscribe(response => {
    //   console.log('POST response: ', response)
    //   })
    // })
    this.authService.getLoggedUser().subscribe(
      data => {
        this.user = data;
        console.log('privileges u getLogged', this.user?.privileges)
        let role = this.user.privileges.find(obj => {
          return obj === 'ROLE_USER'
        })
        console.log('magija', role)
      }
    );

    if (this.user == null) {
      //send whoami to server to re-authenticate
      this.authService.whoami().subscribe(
        data => {
          this.user = data;
          console.log('privileges u whoAmI', this.user?.privileges)
          let role = this.user.privileges.find(obj => {
            return obj[0]
          })
          console.log('magija', role)
        },
        error => {
          console.log(error)
        }
      );

     }

     console.log('privileges', this.user?.privileges)
  }

  logout() {
    this.authService.logout();
    this.user = null;
  }

}
