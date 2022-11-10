import SwiftUI
import shared

struct ContentView: View {
	let greet = Greeting().greeting()
    
    @StateObject var greetingInfo = GreetingInfo()


	var body: some View {
        VStack {
            Text("\(greet)")
            Text("Repo number: \(greetingInfo.number)")
            
            List(greetingInfo.todos, id: \.id) { todo in
                Text("\(todo.id). \(todo.title), Completed: \(todo.completed.description)")
            }
        }
            .onAppear {
                greetingInfo.start()
            }
	}
}

class GreetingInfo: ObservableObject {
    @Published var number: Int = -1
    @Published var todos: [Todo] = []
    
    let viewModel = GreetingViewModel()
    
    func start() {
        viewModel.state.watch { state in
            self.number = state as! Int
        }
        viewModel.todos.watch { todos in
            self.todos = todos as! [Todo]
        }
//        GreetingRepo().numbersCommonFlow().watch { number in
//            self.number = number as! Int
//        }
    }
}

struct ContentView_Previews: PreviewProvider {
	static var previews: some View {
		ContentView()
	}
}
