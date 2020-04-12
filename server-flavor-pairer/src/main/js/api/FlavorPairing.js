import React, { Component } from 'react';

import IngredientPairingRank from './IngredientPairingRank';

class FlavorPairing extends Component {
   constructor(props) {
      super(props);
   }
   
   isArrayPopulated(array) {
     return Boolean(Array.isArray(array) && array.length);
   }

   render() {
     let foundFirstLevelIngredientPairings = this.isArrayPopulated(this.props.flavor.firstLevelIngredientPairings);
     let foundSecondLevelIngredientPairings = this.isArrayPopulated(this.props.flavor.secondLevelIngredientPairingRanks);
     let foundThirdLevelIngredientPairings = this.isArrayPopulated(this.props.flavor.secondLevelIngredientPairingRanks);

     if (!(foundFirstLevelIngredientPairings && foundSecondLevelIngredientPairings && foundThirdLevelIngredientPairings)) {
      return (
        <div>
          <p>No Ingredient Pairings Found</p>
        </div> 
      )
    }
   
     let firstLevelDisplay = <p>No First Level Ingredient Pairings Found</p>;
     if (foundFirstLevelIngredientPairings)
     {
       firstLevelDisplay = (
          <div>
            <h3>First Level Ingredient Pairings:</h3>
            <ul>
              {this.props.flavor.firstLevelIngredientPairings.map(v => <li key={v.toString()}>{v}</li> )}
            </ul>
          </div>       
       );
     }
     
     let secondLevelDisplay = <p>No Second Level Ingredient Pairings Found</p>;
     if (foundSecondLevelIngredientPairings)
     {
          secondLevelDisplay = (
            <div>
              <h3>Second Level Ingredient Pairings:</h3>
              <IngredientPairingRank flavor={this.props.flavor.secondLevelIngredientPairingRanks} />
            </div>
          );
     }

     let thirdLevelDisplay = <p>No Third Level Ingredient Pairings Found</p>;
     if (foundThirdLevelIngredientPairings)
     {
          thirdLevelDisplay = (
            <div>
              <h3>Third Level Ingredient Pairings:</h3>
              <IngredientPairingRank flavor={this.props.flavor.thirdLevelIngredientPairingRanks} />
            </div>
          );
     }
   
   
      return (
        <div>
          {firstLevelDisplay}
          {secondLevelDisplay}
          {thirdLevelDisplay}
        </div>     
      )
   }
}

export default FlavorPairing