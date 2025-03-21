import { lastValueFrom, Observable } from "rxjs";
import { ConfirmOrder, MenuItem } from "./models";
import { inject, Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";

@Injectable()
export class RestaurantService {
  
  private http = inject(HttpClient)

  selectedItems: MenuItem[] = []

  // TODO: Task 2.2
  // You change the method's signature but not the name
  getMenuItems(): Observable<MenuItem[]> {
    return this.http.get<MenuItem[]>("/api/menu")
  }

  updateSelectedItems(menuItems : MenuItem[]){
    this.selectedItems= menuItems
  } 

  getSelectedItems():MenuItem[]{
    return this.selectedItems
  }

  discardSelectedItems(){
    this.selectedItems = []
  }

  // TODO: Task 3.2

  placeOrder(payload: ConfirmOrder){
    return lastValueFrom(
      this.http.post<any>('/api/food_order',payload)
    )
  }

 
}
