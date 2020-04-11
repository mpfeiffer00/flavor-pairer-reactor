import React, { Component } from 'react';

class IngredientPairingRank extends Component {
   constructor(props) {
      super(props);
   }

   render() {
     let items = <p></p>;
     if (this.props.flavor)
     {
       items = this.props.flavor.map(v =>
           <li key={v.toString()}>{v.ingredient} with rank {v.rank}</li>
         );
     }
        
     return (
       <ul>
         {items}
       </ul>  
     )
   }
}

export default IngredientPairingRank