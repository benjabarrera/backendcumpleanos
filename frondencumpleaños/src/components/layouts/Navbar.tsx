
import { Menu, Bell } from 'lucide-react';
import { useUiStore } from '../../app/store/uiStore';
import { Avatar } from '../ui/Avatar';
import { useAuthStore } from '../../app/store/authStore';

export const Navbar = () => {
  const toggleSidebar = useUiStore((state) => state.toggleSidebar);
  const user = useAuthStore((state) => state.user);

  return (
    <header className="sticky top-0 z-30 flex h-16 w-full items-center justify-between border-b border-border bg-surface/80 px-4 sm:px-6 backdrop-blur">
      <div className="flex items-center gap-4">
        <button
          onClick={toggleSidebar}
          className="rounded-md p-2 text-text-secondary hover:bg-elevated hover:text-text-primary focus:outline-none"
        >
          <Menu className="h-5 w-5" />
        </button>
      </div>
      <div className="flex items-center gap-4">
        <button className="rounded-full p-2 text-text-secondary hover:bg-elevated hover:text-text-primary">
          <Bell className="h-5 w-5" />
        </button>
        <Avatar name={user?.nombre || 'User'} size="sm" />
      </div>
    </header>
  );
};
