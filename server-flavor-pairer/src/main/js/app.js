'use strict';

const React = require('react');
const ReactDOM = require('react-dom');

import IngredientPairingComponent from './api/IngredientPairingComponent';

class NameForm extends React.Component {
   constructor(props) {
      super(props);
      this.state = {value: ''};
      this.handleChange = this.handleChange.bind(this);
      this.handleSubmit = this.handleSubmit.bind(this);
   }

   handleChange(event) {
     // TODO: add search here
     this.setState({value: event.target.value});
   }

   handleSubmit(event) {
     this.setState({value: event.target.value});
     event.preventDefault();
   }

   render() {
     return (
       <div>
         <form>
           <label>
             Enter Ingredient:
             <input type="text" value={this.state.value} onChange={this.handleChange} />
           </label>
         </form>
         <IngredientPairingComponent ingredient={this.state.value}/>
       </div>
     );
   }
}

class App extends React.Component {
   constructor(props) {
      super(props);
      this.state = {ingredients: []};
   }

   componentDidMount() {
      fetch('/api/')
      .then(response => { return response.json() })
      .then(results => this.setState({ingredients: results}));  
   }

   render() {
     return (
       <IngredientList ingredients={this.state.ingredients}/>
     );
   }
}

class IngredientList extends React.Component{
   render() {  
      return (
         <select>
           {this.props.ingredients.map(ingredient =>
             <option value={ingredient} key={ingredient.toString()}>{ingredient}</option>
           )}
         </select>
      )
   }
}

ReactDOM.render(
   <NameForm />, 
   document.getElementById('react')
)
