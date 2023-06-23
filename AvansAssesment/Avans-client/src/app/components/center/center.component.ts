import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { nanoid } from 'nanoid';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-center',
  templateUrl: './center.component.html',
  styleUrls: ['./center.component.css']
})
export class CenterComponent {
  longUrl: string ="";
  shortUrl: string ="";

  constructor(private http: HttpClient, private route: ActivatedRoute, private router: Router) {}

  generateShortUrl() {
    // Validate the URL structure
    const urlPattern = /^https?:\/\/\w+(\.\w+)*(:\d+)?(\/\S*)?$/i;
    if (!urlPattern.test(this.longUrl)) {
      alert('Invalid URL');
      return;
    }

    // Check if the URL already exists in the database
    this.http.get('http://localhost:7000/urls/' + encodeURIComponent(this.longUrl))
      .subscribe(

        (data: any) => {

        // If the URL already exists, use the existing short URL
          this.shortUrl = 'http://localhost:7000/' + data.shortId;
        },
        (error) => {
        // If the URL does not exist, generate a new short URL
          const shortId = nanoid(10);
          this.http.post('http://localhost:7000/urls', {
            longUrl: this.longUrl,
            shortId: shortId,
            expiresAt: new Date(Date.now() + 5 * 60 * 1000) // 5 minutes from now
          }).subscribe(
            () => {
              this.shortUrl = 'http://localhost:7000/' + shortId;
            },
            (error) => {
              alert('Failed to generate short URL');
            }
          );
        }
      );
  }


  // Search with this format =  http://localhost:7000/urls/ + shorturl generated in database
  redirectToLongUrl() {
    const shortId = this.route.snapshot.paramMap.get('shortId');
    
    // Look up the long URL corresponding to the short ID
    this.http.get('http://localhost:7000/urls/' + shortId)
      .subscribe(
        (data: any) => {
          const expiresAt = new Date(data.expiresAt);
          if (expiresAt < new Date()) {
            alert('This short URL has expired');
            this.router.navigateByUrl('/');
          } else {
            window.location.href = data.longUrl;
          }
        },
        (error) => {
          alert('This short URL does not exist');
          this.router.navigateByUrl('/');
        }
      );
  }
}
