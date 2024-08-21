import MenuItem from '@_components/MenuItem/MenuItem';

interface Menu {
  description: string;
  onClick: () => void;
}

interface MenuItemListProps {
  menus: Menu[];
}

export default function MenuItemList(props: MenuItemListProps) {
  const { menus } = props;
  return (
    <div>
      {menus.map((menu, index) => (
        <MenuItem
          {...menu}
          key={menu.description}
          isLastItem={menus.length - 1 === index}
        />
      ))}
    </div>
  );
}
