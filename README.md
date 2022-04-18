
<!-- TABLE OF CONTENTS -->
<!-- TABLE OF CONTENTS -->
<details>
  <summary>Table of Contents</summary>
  <ol>
    <li><a href="#about-the-project">About The Project</a></li>
    <li><a href="#database-design">Database design</a></li>
    <li><a href="#built-with">Built With</a></li>
    <li><a href="#getting-started">Getting Started</a></li>
    <li><a href="#installation">Installation</a></li>
    <li><a href="#demo">Demo</a></li>
  </ol>
</details>



<!-- ABOUT THE PROJECT -->
## About The Project

![Screenshot 2022-04-18 at 9 35 17 AM](https://user-images.githubusercontent.com/13139667/163741044-4da1c107-b6d7-4d1e-a8aa-c3651e498f80.png)



The portfolio consist of three types of products: Common stocks, European call options on common stocks, European put options on common stocks. And the portfolio system calculate maket value and total NAV of those supported securities in real time. To implement the portfolio system, we need to make use of mock market data to perform data feed (like stock prices) in a discrete time.

<p align="right">(<a href="#top">back to top</a>)</p>


<!-- Database design -->
## Database design
![Screenshot 2022-04-18 at 9 27 58 AM](https://user-images.githubusercontent.com/13139667/163740637-e47f5e67-455d-40de-b778-be84857a9cb6.png)
Since stocks may have the underlying securities which are european call/put options in this exercise. Therefore, one stock may be having many european options.

<p align="right">(<a href="#top">back to top</a>)</p>


### Built With

* Spring boot 2.6.6
* H2 database
* Lombok
* Apache Commons Math3 3.6.1
* Java 11


<p align="right">(<a href="#top">back to top</a>)</p>



<!-- GETTING STARTED -->
## Getting Started

This is an example of how you may give instructions on setting up your project locally.
To get a local copy up and running follow these simple example steps.

### Prerequisites

This is an example of how to list things you need to use the software and how to install them.
* JDK 11


### Installation

1. Get the jar file and cd into destination
Download: https://drive.google.com/file/d/1SLB-JiL2yikB6Cl_HYj0PUgWSfmp3FnP/view
2. For Windows
   ```sh
   java -jar Portfolio_System.jar
   ```
3. For Mac
   ```sh
   java -jar Portfolio_System.jar
   ```

<p align="right">(<a href="#top">back to top</a>)</p>



<!--  Demo -->
## Demo

1. Stock and options set up in StockServiceImpl. For this exercise, I just set a "TSLA" stock with two options & a "AAPL" stock with one options. The init templates are shown below, feel free to add some securities.
  Stocks:
   ```sh
           Stock tickerA = Stock.builder()
                .tickerId("TSLA")
                .price(700.0)
                .mu(0.5)
                .numberOfShare(2)
                .annualizedSD(0.3)
                .positionType(PositionType.LONG)
                .build();
           createStock(tickerA)
   ```
     
   Options:
   ```sh
           EuropeanOptions optionsA2 = EuropeanOptions.builder()
                .optionId("TSLA456")
                .strikePrice(800.0)
                .maturityYear(2)
                .stock(tickerA)
                .numberOfContracts(3)
                .optionsType(OptionsType.PUT)
                .positionType(PositionType.SHORT)
                .build();
            europeanOptionsService.createOptions(optionsA2)
   ```
   


2.Start Service

![Screenshot 2022-04-18 at 9 48 55 AM](https://user-images.githubusercontent.com/13139667/163742014-a598acd4-2ac8-4a9f-a502-dd564d1192cd.png)

Those three functions will show each position’s market value & total portfolio’s NAV.

3.Select Printing Portfolio on change. By Selecting "Printing Portfolio on change", user can keep monitoring the portfolio whenever there is a change in the market value for any position in the portfolio.

![Screenshot 2022-04-18 at 9 54 16 AM](https://user-images.githubusercontent.com/13139667/163742442-dc1381da-1a20-4343-945f-e354baa5a387.png)

4.Select Printing portfolio on demand. The latest market value for all positions and the NAV of the whole portfolio will be shown in console.

![Screenshot 2022-04-18 at 9 56 19 AM](https://user-images.githubusercontent.com/13139667/163742602-9506c3fa-6b88-4720-90c6-edb7fc6e6940.png)

5.Select Printing portfolio on demand(as text file). It will generate a report for latest market value for all positions and the NAV of the whole portfolio as text file. And the report will be named as portfolio_report(located in same directory as jar file)

![Screenshot 2022-04-18 at 9 58 09 AM](https://user-images.githubusercontent.com/13139667/163742735-73ee55e6-fe90-4125-ac78-75a23605b350.png)



<p align="right">(<a href="#top">back to top</a>)</p>





<!-- MARKDOWN LINKS & IMAGES -->
<!-- https://www.markdownguide.org/basic-syntax/#reference-style-links -->
[contributors-shield]: https://img.shields.io/github/contributors/github_username/repo_name.svg?style=for-the-badge
[contributors-url]: https://github.com/github_username/repo_name/graphs/contributors
[forks-shield]: https://img.shields.io/github/forks/github_username/repo_name.svg?style=for-the-badge
[forks-url]: https://github.com/github_username/repo_name/network/members
[stars-shield]: https://img.shields.io/github/stars/github_username/repo_name.svg?style=for-the-badge
[stars-url]: https://github.com/github_username/repo_name/stargazers
[issues-shield]: https://img.shields.io/github/issues/github_username/repo_name.svg?style=for-the-badge
[issues-url]: https://github.com/github_username/repo_name/issues
[license-shield]: https://img.shields.io/github/license/github_username/repo_name.svg?style=for-the-badge
[license-url]: https://github.com/github_username/repo_name/blob/master/LICENSE.txt
[linkedin-shield]: https://img.shields.io/badge/-LinkedIn-black.svg?style=for-the-badge&logo=linkedin&colorB=555
[linkedin-url]: https://linkedin.com/in/linkedin_username
[product-screenshot]: images/screenshot.png
