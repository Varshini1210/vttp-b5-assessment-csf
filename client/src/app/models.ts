// You may use this file to create any models
export interface MenuItem {
    id: string
    name: string
    description: string
    price: number
    quantity: number
}

export interface ConfirmOrder{
    username: string
    password: string
    items: Item[]
}

export interface Item {
    id: string
    price: number
    quantity: number
}