import { Component, inject, OnInit } from '@angular/core';
import { RestaurantService } from '../restaurant.service';
import { Observable, Subscription } from 'rxjs';
import { MenuItem } from '../models';
import { Router } from '@angular/router';

@Component({
  selector: 'app-menu',
  standalone: false,
  templateUrl: './menu.component.html',
  styleUrl: './menu.component.css'
})
export class MenuComponent implements OnInit{
  // TODO: Task 2

  private restaurantSvc = inject(RestaurantService)
  private router = inject(Router)

  menu$!: Subscription
  menuItems!: MenuItem[]
  selectedItems: MenuItem[]=[]
  totalPrice: number =0
  

  ngOnInit(): void{
   

    this.menu$ = this.restaurantSvc.getMenuItems().subscribe(
      (items: MenuItem[]) => {
        this.menuItems=items
      }
    )
    //this.quantityInit(this.menuItems)

  }

  // private quantityInit(menuItems: MenuItem[]){
  //   for(let item of menuItems){
  //     console.log(item.name)
  //     item.quantity=0
  //   }
  // }

  protected increaseQuantity(item: MenuItem){
    item.quantity +=1
    this.selectedItems = [...this.selectedItems,item]
    this.totalPrice += item.price
  }

  protected removeItem(itemToRemove: MenuItem){
    this.totalPrice -= itemToRemove.quantity * itemToRemove.price
    itemToRemove.quantity = 0
    this.selectedItems = this.selectedItems.filter(item => item.name !== itemToRemove.name)
    
  }

  protected placeOrder(){
    this.restaurantSvc.updateSelectedItems(this.selectedItems)
    this.router.navigate(['/placeorder'])
  }

  
}
