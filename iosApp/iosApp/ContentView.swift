import SwiftUI
import shared

struct ContentView: View {
    let platformInfo = PlatformInfo().info()
    @StateObject var greetingInfo = GreetingInfo()

	var body: some View {
        VStack {
            Text("Hello, \(platformInfo)")
            Text("Repo number: \(greetingInfo.greetingNumber)")
            
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
    @Published var greetingNumber: Int = -1
    @Published var todos: [Todo] = []
    
    let viewModel = GreetingViewModel()
    
    func start() {
        viewModel.greetingNumber.watch { state in
            self.greetingNumber = state as! Int
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
