'use strict';

const React = require('react');
const ReactDOM = require('react-dom');

// https://reactjs.org/docs/forms.html
class NameForm extends React.Component {
   constructor(props) {
      super(props);
      this.state = {value: ''};
      this.handleChange = this.handleChange.bind(this);
      this.handleSubmit = this.handleSubmit.bind(this);
   }

   handleChange(event) {
     this.setState({value: event.target.value});
   }

   handleSubmit(event) {
     alert('A name was submitted: ' + this.state.value);
     event.preventDefault();
   }

   render() {
     return (
       <form onSubmit={this.handleSubmit}>
         <label>
           Name:
           <input type="text" value={this.state.value} onChange={this.handleChange} />
         </label>
         <input type="submit" value="Submit" />
       </form>
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
