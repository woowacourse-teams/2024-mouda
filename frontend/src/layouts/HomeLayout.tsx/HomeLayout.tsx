import * as S from './MoimList.style';

import HomeFixedButtonWrapper from './HomeFixedButtonWrapper/HomeFixedButtonWrapper';
import HomeHeader from './HomeHeader/HomeHeader';
import HomeMain from './HomeMain/HomeMain';
import { PropsWithChildren } from 'react';
import HomeNav from './HomeNav/HomeNav';

function HomeLayout(props: PropsWithChildren) {
  const { children } = props;

  return <div css={S.containerStyle}>{children}</div>;
}

HomeLayout.Header = HomeHeader;
HomeLayout.Nav = HomeNav;
HomeLayout.Main = HomeMain;
HomeLayout.HomeFixedButtonWrapper = HomeFixedButtonWrapper;

export default HomeLayout;
