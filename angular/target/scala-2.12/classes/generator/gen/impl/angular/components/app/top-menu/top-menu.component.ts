import { Component, OnInit, Input } from '@angular/core';

@Component({
  selector: 'top-menu',
  templateUrl: './top-menu.component.html',
  styleUrls: ['./top-menu.component.css']
})
export class TopMenuComponent implements OnInit {
  @Input() menu:NavMenu;

  constructor() { }

  ngOnInit() {
  }

}

export class NavMenu {
  constructor(public items:NavItem[]){}
}

export class NavItem {
  constructor(public title:string, public items?:MenuItem[], public routerLink?:string){}
}

export class MenuItem {
  constructor(public title:string, public routerLink:string){}
}
