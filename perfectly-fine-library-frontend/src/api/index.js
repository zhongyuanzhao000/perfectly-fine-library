import request from './request';

//用户注册的第一阶段
export function userRegisterFirst(username,password,role) {
    return request('post','/auth/users/verification_id', {
        username: username, password: password, role: role
    })
}

//用户注册的第二阶段
export function userRegisterSecond(id,code) {
    return request('post','/auth/users',{verificationId:id,verificationCode:code});
}

//用户登陆
export function userLogin(auth) {
    return request('get','/auth/users/token',{auth:auth});
}

//添加管理员，即注册管理员
export function addAdmin(username,password) {
    return request('post','/auth/admins',{username:username,password:password});
}

//管理员登陆
export function adminLogin(auth,library) {
    return request('get','/auth/admins/token?library='+library,{auth:auth});
}

//修改用户最大借阅数量
export function modifyConfig(uNum,uBoExp,uReExp,pNum,pBoExp,pReExp,tNum,tBoExp,tReExp) {
    return request('put','/admin/configs',
        [
           {
                maxBorrowNumber: uNum,
                borrowExpiration: uBoExp,
                reserveExpiration: uReExp,
                role:'UNDERGRADUATE'
            },
            {
                maxBorrowNumber: pNum,
                borrowExpiration: pBoExp,
                reserveExpiration: pReExp,
                role:'POSTGRADUATE'
            },
            {
                maxBorrowNumber: tNum,
                borrowExpiration: tBoExp,
                reserveExpiration: tReExp,
                role:'TEACHER'
            }]
)
}

//获得用户最大借阅数量等设置信息
export function getConfig() {
    return request('get','/admin/configs')
}

//管理员进行现场借书
export function borrowBook(copyId,username) {
    //return request('post','/orders/copies/'+copyId+'/borrow?username='+username);
    return request('post','/orders?operation=Borrow&copyId='+copyId+'&username='+username);
}

export function returnBook(copyId) {
    //return request('post','/orders/copies/'+copyId+'/return');
    return request('post','/orders?operation=Return&copyId='+copyId);
}

export function reserveBook(copyId) {
    //return request('put','/orders/copies/'+copyId+'/reserve');
    return request('post','/orders?operation=Reserve&copyId='+copyId);
}

export function bookDamage(copyId) {
    //return request('put','/orders/copies/'+copyId+'/damage')
    return request('post','/orders?operation=Damage&copyId='+copyId);
}

export function bookLost(copyId) {
    //return request('put','/orders/copies/'+copyId+'/lose')
    return request('post','/orders?operation=Lose&copyId='+copyId);
}


//根据copyId或者username获取记录信息
export function getOrderByCopyIdUsername(copyId,username) {
    if (copyId === ''){
        return request('get','/orders?username='+username)
    }
    if (username === ''){
        return request('get','/orders?copyId='+copyId)
    }
}

export function getOrderState(orderId) {
    return request('get','/orders/'+orderId);
}

//管理员一键发送邮件
export function sendEmail() {
    return request('get','/admin/notify')
}

//根据commentId找到所属reply list
export function getRepliesByCommentId(commentId) {
    return request('get','/comments/'+commentId+'/replies')
}

//重置用户信用
export function resetUserCredit(username) {
    return request('get','/users/'+username+'/reset/credit')
}
export function getReservedCopies(username) {
    return request('get','/copies?username='+username+'&status=Reserved');
}

export function getBorrowedCopies(username) {
    return request('get','/copies?username='+username+'&status=Borrowed');
}



export function uploadBook(title,author,description,isbn,publicationDate,library,coverURL,price) {
    return request('post','/books',{title:title, author:author,
        brief:description, isbn:isbn, publicationDate:publicationDate,
        library:library, cover:coverURL,price:price});
}

export function getBookFromTitleIsbn(title,isbn) {
    if(title==='') {
        return request('get', '/books?isbn='+isbn);
    }
    if(isbn===''){
        return request('get', '/books?title='+title)
    }
}

export function titleSearch(title) {
    return request('get','/books?title='+title);
}

export function isbnSearch(isbn) {
    return request('get','/books?isbn='+isbn);
}


//根据bookId或者username获取图书评论
export function getCommentByBookIdUsername(bookId,username) {
    if (bookId === ''){
        return request('get','/comments?username='+username);
    }
    if (username === ''){
        return request('get','/comments?bookId='+bookId)
    }
}

//上传副本
export function uploadCopy(bid,num) {
    return request('post','/copies',{bookId:bid,
        number:num});
}

export function getIndexBooks() {
    return request('get','/books');
}


//用户修改密码
export function changePassword(password) {
    return request('put','/auth/users',{password:password});
}

//获取用户的详情信息
export function getUserInfo() {
    return request('get','/users/my');
}

export function getCopyFromBid(bid) {
    return request('get','/copies?bookId='+bid);
}

export function getBookFromBookId(bookId) {
    return request('get','/books/'+bookId)
}

export function getCopyFromIsbn(isbn) {
    return request('get','/copies?isbn='+isbn)
}

export function getCopyFromCopyId(copyId) {
    return request('get','/copies/'+copyId)
}


export function payBill() {
    return request('get','/users/pay_fine')
}

export function  addComment(bookId,rate,content) {
    return request('post','/comments',{bookId:bookId,rate:rate,content:content})
}

export function delComment(commentId) {
    return request('put','/comments/'+commentId);
}

export function delReply(replyId) {
    return request('put','/comments/replies/'+replyId);
}

export function addReply(commentId,content,toId) {
    return request('post','/comments/'+commentId+'/replies',{commentId:commentId,content:content,toId:toId})
}
