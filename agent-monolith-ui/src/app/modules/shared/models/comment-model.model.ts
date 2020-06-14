export class CommentModel{
    id:number;
    title: string;
    content: string;
    rating: number;

    constructor(title?: string, content?: string, rating?: number){
        this.title = title;
        this.content = content;
        this.rating = rating;
    }
}