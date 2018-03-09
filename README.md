# Roads

## Usage

### Authentication
##### /user/register

| Parameters | Headers |
| -----------|-------- |
| login |  |
| password |  |
| email |  |

##### /user/get/{login}

##### /user/auth

| Parameters | Headers |
| -----------|-------- |
| login |  |
| password |  |


### Map

##### /map/point/add
| Parameters | Headers |
| -----------|-------- |
| latitude | authString |
| longitude |  |


##### /map/point/{id}/delete

| Parameters | Headers |
| -----------|-------- |
|  | authString |

##### /map/point/{id}/note/add

| Parameters | Headers |
| -----------|-------- |
| text | authString |


##### /map/point/{id}/note/{note_id}/remove

| Parameters | Headers |
| -----------|-------- |
|  | authString |

