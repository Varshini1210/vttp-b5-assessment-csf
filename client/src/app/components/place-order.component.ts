import { Component, inject, OnInit } from '@angular/core';
import { RestaurantService } from '../restaurant.service';
import { Router } from '@angular/router';
import { ConfirmOrder, Item, MenuItem } from '../models';
import { Observable } from 'rxjs';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-place-order',
  standalone: false,
  templateUrl: './place-order.component.html',
  styleUrl: './place-order.component.css'
})
export class PlaceOrderComponent implements OnInit{

  private restaurantSvc = inject(RestaurantService)
  private router = inject(Router)
  private fb = inject(FormBuilder)

  protected form!: FormGroup 

  orderItems!: MenuItem[]
  total: number = 0
  itemPayload: Item[] =[]
  
  ngOnInit(): void {
    this.orderItems = this.restaurantSvc.getSelectedItems()
    this.calculateTotal(this.orderItems)

    this.form = this.createForm()
  }

  // TODO: Task 3

  protected calculateTotal(orderItems:MenuItem[]) {
    
    for(let item of orderItems){
      this.total += (item.quantity * item.price)
    }
    
  }

  private createForm(): FormGroup {
    return this.fb.group({
      username: this.fb.control<string>('',[Validators.required]),
      password: this.fb.control<string>('',Validators.required)

    })
  }

  protected startOver(){
    this.restaurantSvc.discardSelectedItems()
    this.router.navigate(['/'])
  }

  private formItemsPayload(orderItems: MenuItem[]){
    for(let item of orderItems){
      const orderItem: Item ={
        id: item.id,
        price: item.price,
        quantity: item.quantity
      }
      this.itemPayload=[...this.itemPayload,orderItem]
    }
  }

  protected processForm(){
    this.formItemsPayload(this.orderItems)
    const payload: ConfirmOrder = {
      username: this.form.value['username'],
      password: this.form.value['password'],
      items: this.itemPayload
    }
    this.restaurantSvc.placeOrder(payload)

    }
  

}
