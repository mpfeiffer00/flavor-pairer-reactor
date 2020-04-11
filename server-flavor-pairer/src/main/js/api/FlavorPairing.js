import React, { Component } from 'react';

import IngredientPairingRank from './IngredientPairingRank';

class FlavorPairing extends Component {
   constructor(props) {
      super(props);
   }

   render() {
     let firstShit = <p>first shit empty</p>;
     if (this.props.flavor.firstLevelIngredientPairings)
     {
       firstShit = (
          <div>
            <h3>First Level Ingredient Pairings:</h3>
            <ul>
              {this.props.flavor.firstLevelIngredientPairings.map(v => 
                <li key={v.toString()}>{v}</li>
              )}
            </ul>
          </div>       
       );
     }
     
     let secondShit = <p>second shit empty</p>;
     if (this.props.flavor.secondLevelIngredientPairingRanks)
     {
          secondShit = (
            <div>
              <h3>Second Level Ingredient Pairings:</h3>
              <IngredientPairingRank flavor={this.props.flavor.secondLevelIngredientPairingRanks} />
            </div>
          );
     }

     let thirdShit = <p>third shit empty</p>;
     if (this.props.flavor.thirdLevelIngredientPairingRanks)
     {
          thirdShit = (
            <div>
              <h3>Third Level Ingredient Pairings:</h3>
              <IngredientPairingRank flavor={this.props.flavor.thirdLevelIngredientPairingRanks} />
            </div>
          );
     }
   
   
      return (
        <div>
          {firstShit}
          
          {secondShit}
          
          {thirdShit}
        </div>     
      )
   }
}

export default FlavorPairing