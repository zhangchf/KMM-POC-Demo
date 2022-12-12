import SwiftUI
import shared

struct ContentView: View {
    let platformInfo = PlatformInfo().info()
    @StateObject var greetingInfo = GreetingInfo()

	var body: some View {
        VStack {
            Text("Hello, \(platformInfo)")
            Text("Repo number: \(greetingInfo.greetingNumber)")
            
            let users = greetingInfo.state.users
            let buttonText = users.isEmpty ? "Click to get users" : "Refresh users"
            Button(buttonText) {
                greetingInfo.getUsers()
            }.padding(.top, 10)
            List(users, id: \.id) { user in
                Text("\(user.id). \(user.name), \(user.email)")
            }
            
            List(greetingInfo.state.todos, id: \.id) { todo in
                Text("\(todo.id). \(todo.title), Completed: \(todo.completed.description)")
            }
        }
            .onAppear {
                greetingInfo.start()
            }
            .alert(isPresented: $greetingInfo.showError) {
                Alert(title: Text("Error"), message: Text("\(greetingInfo.state.error)"), dismissButton: .default(Text("OK")))
            }
	}
}

class GreetingInfo: ObservableObject {
    @Published var greetingNumber: Int = -1
    @Published var todos: [Todo] = []
    @Published var state: GreetingState = GreetingState(todos: [], users: [], error: "") {
        didSet {
            showError = !state.error.isEmpty
        }
    }
    @Published var showError: Bool = false
    
    private var viewModel: GreetingViewModel
    init() {
        viewModel = GreetingViewModel()
    }
    
    func start() {
        viewModel.greetingNumber.watch { number in
            self.greetingNumber = number as! Int
        }
        viewModel.states.watch { state in
            self.state = state!
        }
//        GreetingRepo().numbersCommonFlow().watch { number in
//            self.number = number as! Int
//        }
    }
    
    func getUsers() {
        viewModel.getUsers()
    }
}

struct ContentView_Previews: PreviewProvider {
	static var previews: some View {
		ContentView()
	}
}
