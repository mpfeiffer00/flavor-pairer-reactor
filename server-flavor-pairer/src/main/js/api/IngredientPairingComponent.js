import React, { Component } from 'react';
import FlavorPairing from './FlavorPairing';

class IngredientPairingComponent extends React.PureComponent {
   constructor(props) {
      super(props);
      this.state = {ingredient: "", ingredients: []};
   }

   componentDidMount() {
     this.callAPI();
   }
    
   static getDerivedStateFromProps(nextProps, previousState) {
     if(nextProps.ingredient !== previousState.ingredient){
       return {ingredient : nextProps.ingredient};
     }
     else return null;
   }    
    
   componentDidUpdate(prevProps, previousState) {
     if (previousState.ingredient !== this.state.ingredient) {
       this.callAPI();
     }
   }
   
   callAPI() {
     fetch(`/api/flavorpairing?ingredient=${this.state.ingredient}`)
       .then(response => { return response.json() })
       .then(results => this.setState({ingredients: results}));  
   }
      
   render() {
     return (
       <React.Fragment>
         {<h1>Pairings for: {this.props.ingredient}</h1>}
         {<FlavorPairing flavor={this.state.ingredients} />}
       </React.Fragment>
     );
   }

}

export default IngredientPairingComponent